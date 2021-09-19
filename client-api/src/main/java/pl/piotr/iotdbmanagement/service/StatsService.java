package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotr.iotdbmanagement.connectionstats.ConnectionStats;
import pl.piotr.iotdbmanagement.connectionstats.ConnectionStatsRepository;

import java.time.LocalDate;

@Service
public class StatsService {
    private ConnectionStatsRepository connectionStatsRepository;

    @Autowired
    public StatsService(ConnectionStatsRepository connectionStatsRepository) {
        this.connectionStatsRepository = connectionStatsRepository;
    }

    public int getAllTodaySuccess() {
        return connectionStatsRepository.findAllByDate(LocalDate.now())
                .stream()
                .mapToInt(ConnectionStats::getSuccessfulConnections)
                .sum();
    }

    public int getAllTodayFailures() {
        return connectionStatsRepository.findAllByDate(LocalDate.now())
                .stream()
                .mapToInt(ConnectionStats::getFailureConnections)
                .sum();
    }

}
