package edu.hw3.task6;

import java.util.PriorityQueue;

public class StockMarketImpl implements StockMarket {
    private final PriorityQueue<Stock> stockPriorityQueue;

    public StockMarketImpl() {
        // Инициализируем PriorityQueue с компаратором для сортировки по убыванию цены
        stockPriorityQueue = new PriorityQueue<>((stock1, stock2) -> Double.compare(stock2.price(), stock1.price()));
    }

    @Override
    public void add(Stock stock) {
        stockPriorityQueue.add(stock);
    }

    @Override
    public void remove(Stock stock) {
        stockPriorityQueue.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return stockPriorityQueue.peek();
    }
}
