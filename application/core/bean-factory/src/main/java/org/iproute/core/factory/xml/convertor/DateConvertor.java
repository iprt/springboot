package org.iproute.core.factory.xml.convertor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateConvertor
 *
 * @author zhuzhenjie
 */
public class DateConvertor implements Converter<String, Date> {

    private static final DateFormat FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date convert(String str) {
        if (StringUtils.isBlank(str)) {
            return new Date();
        }
        try {
            return FMT.parse(str);
        } catch (Exception e) {
            return new Date();
        }
    }
}
