package com.example.eachadmin.constants;

import java.util.Arrays;
import java.util.List;

public interface SecurityConstants {

    public static final String JWT_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
    public static final String JWT_HEADER = "Authorization";

    public static final List<String> EXCLUDED_PATHS = Arrays.asList("/login", "/image");

}
