package ru.study.inspects;

import ru.study.utils.LogTransformation;

public interface CheckData {
    @LogTransformation
    String check(String data);


}
