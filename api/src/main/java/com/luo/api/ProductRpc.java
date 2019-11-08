package com.luo.api;

import com.googlecode.jsonrpc4j.JsonRpcService;
import com.luo.api.domain.ProductRpcReq;
import com.luo.entity.Product;
import org.springframework.data.domain.Page;

@JsonRpcService("/products")
public interface ProductRpc {

    Page<Product> query(ProductRpcReq req);

    Product fidOe(String id);
}
