package edu.hw3.task6;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class StockMarketImplTest {
    @Test
    void mostValuableStock() {
        // given
        StockMarket stockMarket = new StockMarketImpl();
        stockMarket.add(new Stock("Apple", 150.25));
        stockMarket.add(new Stock("Microsoft", 200.75));
        stockMarket.add(new Stock("Google", 300.50));

        // when
        Stock mostValuableStock = stockMarket.mostValuableStock();

        // then
        assertThat(mostValuableStock.price()).isEqualTo(300.50);
    }

    @Test
    void add() {
        // given
        StockMarket stockMarket = new StockMarketImpl();
        Stock appleStock = new Stock("Apple", 150.25);

        // when
        stockMarket.add(appleStock);

        // then
        assertThat(stockMarket.mostValuableStock()).isEqualTo(appleStock);
    }

    @Test
    void remove() {
        // given
        StockMarket stockMarket = new StockMarketImpl();
        Stock appleStock = new Stock("Apple", 150.25);

        // when
        stockMarket.remove(appleStock);

        // then
        assertThat(stockMarket.mostValuableStock()).isNull();
    }
}
