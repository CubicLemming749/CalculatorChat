package me.andrestube.calculatorchat.math;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * New {@link MathService} created to replace the old MathHandler.
 */
public class MathService {
    private final ExecutorService executorService;

    public MathService(){
        this.executorService = Executors.newFixedThreadPool(2);
    }

    /**
     * Calculate the given expression in an asynchronous way.
     * @param expression The expression.
     * @return The optional result, in a future.
     * @author AndresTube
     * @author CubicLemming749
     */
    public CompletableFuture<Optional<Double>> calculateExpression(String expression){
        return CompletableFuture.supplyAsync(() -> {
            try {
                Expression exp = new ExpressionBuilder(expression).build();
                Double result = exp.evaluate();
                return Optional.of(result);
            } catch (Exception e) {
                return Optional.empty();
            }
        }, executorService);
    }
}
