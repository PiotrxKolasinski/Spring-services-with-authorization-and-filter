package com.kolasinski.piotr.authorization.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair<T, U> {
    private T var1;
    private U var2;
}