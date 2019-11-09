package com.luo.api;

import com.googlecode.jsonrpc4j.JsonRpcService;
import com.luo.api.domain.ProductRpcReq;
import com.luo.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

//不以 / 开始
@JsonRpcService("rpc/products")
public interface ProductRpc {

    List<Product> query(ProductRpcReq req);

    Product findOne(String id);
}
