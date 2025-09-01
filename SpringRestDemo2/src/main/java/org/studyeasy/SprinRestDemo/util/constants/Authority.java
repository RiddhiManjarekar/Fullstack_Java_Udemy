package org.studyeasy.SprinRestDemo.util.constants;

public enum Authority {
    READ,
    WRITE,
    UPDATE,
    USER, //can  UPDATE  DELETE self object, READ anything
    ADMIN // can read update delete any object
}
