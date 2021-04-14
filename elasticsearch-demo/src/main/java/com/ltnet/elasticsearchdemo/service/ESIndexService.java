package com.ltnet.elasticsearchdemo.service;

import java.io.IOException;

public interface ESIndexService {
    public boolean checkIndex(String index) throws IOException;
}
