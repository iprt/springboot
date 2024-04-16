package org.iproute.core.factory.xml.convertor;


import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MoreDateConvertor
 *
 * @author devops@kubectl.net
 */
public class MoreDateConvertor implements Converter<String, Date> {

    private final DateFormat DF;

    public MoreDateConvertor(String patten) {
        this.DF = new SimpleDateFormat(patten);
    }

    @Override
    public Date convert(String str) {
        if (StringUtils.isBlank(str)) {
            return new Date();
        }
        try {
            return DF.parse(str);
        } catch (Exception e) {
            return new Date();
        }
    }
}
