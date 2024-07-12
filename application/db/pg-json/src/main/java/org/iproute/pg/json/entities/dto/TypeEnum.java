package org.iproute.pg.json.entities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.iproute.pg.json.bo.DynamicTypeRow;

import java.util.function.Function;

/**
 * TypeEnum
 *
 * @author tech@intellij.io
 */
@RequiredArgsConstructor
@ToString
@Slf4j
public enum TypeEnum {
    UNKNOWN(-1, null),

    STUDENT(1000, DynamicTypeRow::getStu),

    TEACHER(2000, DynamicTypeRow::getTeacher),
    ;

    private final int type;
    private final Function<DynamicTypeRow, Object> getter;

    @JsonValue
    public int getType() {
        return type;
    }

    @JsonIgnore
    public Function<DynamicTypeRow, Object> getGetter() {
        return getter;
    }

    @JsonCreator
    public static TypeEnum fromType(final int type) {
        log.info("fromType: {}", type);
        TypeEnum target;
        if (type >= 1000 && type < 2000) {
            target = STUDENT;
        } else if (type >= 2000 && type < 3000) {
            target = TEACHER;
        } else {
            target = UNKNOWN;
        }
        log.info("targetType: {}", target.getType());
        return target;
    }

}
