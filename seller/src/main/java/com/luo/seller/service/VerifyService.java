package com.luo.seller.service;

import com.luo.entity.VerificationOrder;
import com.luo.seller.enums.ChanEnum;
import com.luo.seller.repobackup.VerifyRepository;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//对账
@Service
public class VerifyService {

    @Autowired
    private VerifyRepository verifyRepository;

    @Value("${verification.rootdir:/opt/verification}")
    private String rootDir;

    public File makeVerificationFile(String rootDir, String chanId, Date day) {

        File path = getPath(rootDir, chanId, day);

        if (path.exists()) {
            return path;
        }
        try {
            boolean newFile = path.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String start_str = DAY_FORMAT.format(day);
        Date start = null;
        try {
            start = DAY_FORMAT.parse(start_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date end = new Date(start.getTime() + 1000 * 60 * 60 * 24);
        // List<String> list = verifyRepository.queryVerificationOrders(chanId, start, end);
        String content = String.join(END_LINE, new ArrayList<>());
        FileUtil.writeAsString(path, content);
        return path;

    }

    private static final String END_LINE = System.getProperty("line.separator", "\n");
    private static DateFormat DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public File getPath(String rootDir, String chanId, Date day) {
        String fileName = DAY_FORMAT.format(day) + "-" + chanId;
        File file = Paths.get(rootDir, fileName).toFile();

        return file;

    }

    public static VerificationOrder parseLine(String line) {
        VerificationOrder verificationOrder = new VerificationOrder();
        String[] props = line.split("|");
        verificationOrder.setOrderId(props[0]);
        verificationOrder.setOuterOrderId(props[1]);
        verificationOrder.setChanId(props[2]);
        verificationOrder.setChanUserId(props[3]);
        verificationOrder.setProductId(props[4]);
        verificationOrder.setOrderType(props[5]);
        verificationOrder.setAmount(new BigDecimal(props[6]));
        try {
            verificationOrder.setCreateAt(DATETIME_FORMAT.parse(props[7]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        verificationOrder.setOrderType(props[5]);
        return verificationOrder;

    }

    public void saveChanOrders(String chanId, Date day) {
        ChanEnum conf = ChanEnum.getByChanId(chanId);
        //根ftp 下载对账数据
        File file = getPath(conf.getRootDir(), chanId, day);
        if (!file.exists()) {
            return;
        }
        String content = null;
        try {
            content = FileUtil.readAsString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] lines = content.split(END_LINE);
        List<VerificationOrder> orderList = new ArrayList<>();
        for (String line : lines) {
            orderList.add(parseLine(line));
        }
        //verifyRepository.save(orderList);
        //boot2
        verifyRepository.saveAll(orderList);
    }
}
