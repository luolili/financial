package com.luo.seller.service;

import com.luo.seller.repositories.OrderRepository;
import com.luo.seller.repositories.VerifyRepository;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//对账
@Service
public class VerifyService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private VerifyRepository verifyRepository;

    @Value("${verification.rootdir:/opt/verification}")
    private String rootDir;

    public File makeVerificationFile(String chanId, Date day) {

        File path = getPath(chanId, day);

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
        List<String> list = verifyRepository.queryVerificationOrders(chanId, start, end);
        String content = String.join(END_LINE, list);
        FileUtil.writeAsString(path, content);
        return path;

    }

    private static final String END_LINE = System.getProperty("line.separator", "\n");
    private static DateFormat DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public File getPath(String chanId, Date day) {
        String fileName = DAY_FORMAT.format(day) + "-" + chanId;
        File file = Paths.get(rootDir, fileName).toFile();

        return file;

    }
}
