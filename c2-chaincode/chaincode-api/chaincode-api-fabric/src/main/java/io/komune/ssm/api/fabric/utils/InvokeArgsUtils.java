package io.komune.ssm.api.fabric.utils;

import io.komune.ssm.api.fabric.model.InvokeArgs;

public class InvokeArgsUtils {

    public static String BLOCK_QUERY = "block";
    public static String LIST_QUERY = "list";
    public static String TRANSACTION_QUERY = "transaction";
    public static String QUERY_FUNCTION = "query";

    public static Boolean isBlockQuery(InvokeArgs args) {
        return isQueryOfType(args, BLOCK_QUERY);
    }

    public static Boolean isListQuery(InvokeArgs args) {
        return args.getFunction().equalsIgnoreCase(LIST_QUERY);
    }

    public static Boolean isTransactionQuery(InvokeArgs args) {
        return isQueryOfType(args, TRANSACTION_QUERY);
    }

    private static Boolean isQueryOfType(InvokeArgs args, String type) {
        return !args.getValues().isEmpty() && (
            args.getFunction().equalsIgnoreCase(type) || args.getValues().get(0).equalsIgnoreCase(type)
        );
    }

    public static Boolean isQueryFunction(InvokeArgs args) {
        return args.getFunction().equalsIgnoreCase(QUERY_FUNCTION);
    }

}
