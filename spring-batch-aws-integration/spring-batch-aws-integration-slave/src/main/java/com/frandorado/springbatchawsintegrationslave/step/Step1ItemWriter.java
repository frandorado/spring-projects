package com.frandorado.springbatchawsintegrationslave.step;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class Step1ItemWriter implements ItemWriter<Integer> {
    
    protected static final org.apache.commons.logging.Log logger = LogFactory.getLog(Step1ItemWriter.class);
    
    @Override
    public void write(List<? extends Integer> list) {
        
        for (Integer number : list) {
            if (isPrime(number)) {
                logger.info("The number " + number + " is prime");
            }
        }
    }
    
    /**
     * https://www.mkyong.com/java/how-to-determine-a-prime-number-in-java/
     * 
     * @param n
     * @return
     */
    boolean isPrime(int n) {
        //check if n is a multiple of 2
        if (n % 2 == 0)
            return false;
        //if not, then just check the odds
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }
}
