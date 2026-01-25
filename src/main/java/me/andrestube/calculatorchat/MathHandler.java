package me.andrestube.calculatorchat;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import lombok.Setter;

// mathhandler is what handles the operations in real time (obviously)
@Setter
public class MathHandler {
    public static double calculate(String expression) {
        try {
            // run the handle in a different string from the main
            return CompletableFuture.supplyAsync(() -> {
                // using exp4j
                net.objecthunter.exp4j.Expression e = new net.objecthunter.exp4j.ExpressionBuilder(expression).build();
                double result = e.evaluate();
                return result;
                // and a timeout for more security
            }).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return Double.NaN;
        }

    }
}
