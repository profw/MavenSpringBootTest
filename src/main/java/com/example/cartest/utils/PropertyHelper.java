package com.example.cartest.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public class PropertyHelper {
    /**
     * Set value by field name using appropriate setter method of given object
     *
     * @param obj       Target object
     * @param fieldName Field name in target object
     * @param value     Value of field
     * @return true if value is successfully set or false in case of any error
     */
    public static boolean callSetter(Object obj, String fieldName, Object value) {
        PropertyDescriptor descriptor;
        try {
            descriptor = new PropertyDescriptor(fieldName, obj.getClass());
            Class<?>[] parameterTypes = descriptor.getWriteMethod().getParameterTypes();

            descriptor.getWriteMethod().invoke(obj, parameterTypes[0].cast(value));
            return true;
        } catch (InvocationTargetException | IntrospectionException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
}
