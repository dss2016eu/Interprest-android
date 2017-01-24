package coop.biantik.traductor.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TaskResult {

    int errorCode = 0;

    Object result;
}
