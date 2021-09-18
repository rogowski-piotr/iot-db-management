package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotr.iotdbmanagement.connectionstats.ConnectionStats;
import pl.piotr.iotdbmanagement.connectionstats.ConnectionStatsRepository;

@Service
public class StatsService {
    private ConnectionStatsRepository connectionStatsRepository;

    @Autowired
    public StatsService(ConnectionStatsRepository connectionStatsRepository) {
        this.connectionStatsRepository = connectionStatsRepository;
    }

    public int getAllSuccessStats() {
        return connectionStatsRepository.findAll()
                .stream()
                .mapToInt(ConnectionStats::getSuccessfulConnections)
                .sum();
    }

    public int getAllFailuresStats() {
        return connectionStatsRepository.findAll()
                .stream()
                .mapToInt(ConnectionStats::getFailureConnections)
                .sum();
    }

}
