package Asid.G1.saga;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.eventuate.tram.commands.common.Command;
import io.eventuate.tram.commands.producer.CommandProducer;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import io.eventuate.tram.messaging.consumer.MessageHandler;
import io.eventuate.tram.messaging.consumer.MessageSubscription;
import io.eventuate.tram.sagas.common.SagaLockManager;
import io.eventuate.tram.sagas.orchestration.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Configuration
public class SagaConfiguration {

    @Bean
    public CreateStudentSaga createStudentSaga(KafkaProducer kafkaProducer,
                                               ObjectMapper objectMapper) {
        return new CreateStudentSaga(kafkaProducer, objectMapper);
    }

    @Bean
    public SagaInstanceRepository sagaInstanceRepository() {
        return new InMemorySagaInstanceRepository();
    }

    @Bean
    public CommandProducer commandProducer() {
        return new CommandProducer() {
            @Override
            public String send(String channel, Command command, String replyTo, Map<String, String> headers) {
                // Actual implementation to send commands
                return null;
            }

            @Override
            public String sendNotification(String channel, Command command, Map<String, String> headers) {
                // Actual implementation to send notifications
                return null;
            }

            @Override
            public String send(String channel, String resource, Command command, String replyTo, Map<String, String> headers) {
                // Actual implementation to send commands with resource
                return null;
            }
        };
    }

    @Bean
    public MessageConsumer messageConsumer() {
        return new MessageConsumer() {
            @Override
            public MessageSubscription subscribe(String subscriberId, Set<String> channels, MessageHandler handler) {
                // Actual implementation to subscribe to messages
                return null;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public void close() {
                // Actual implementation to close the consumer
            }
        };
    }

    @Bean
    public SagaLockManager sagaLockManager() {
        return new SagaLockManager() {
            @Override
            public boolean claimLock(String sagaType, String sagaId, String target) {
                // Actual implementation to claim lock
                return false;
            }

            @Override
            public void stashMessage(String sagaType, String sagaId, String target, Message message) {
                // Actual implementation to stash message
            }

            @Override
            public Optional<Message> unlock(String sagaId, String target) {
                // Actual implementation to unlock message
                return Optional.empty();
            }
        };
    }

    @Bean
    public SagaCommandProducer sagaCommandProducer() {
        return new SagaCommandProducer() {
            @Override
            public String sendCommands(String sagaType, String sagaId, List<CommandWithDestinationAndType> commands, String sagaReplyChannel) {
                // Actual implementation to send commands
                return null;
            }
        };
    }

    @Bean
    public SagaManager<CreateStudentSagaData> createStudentSagaManager(Saga<CreateStudentSagaData> saga,
                                                                       SagaInstanceRepository sagaInstanceRepository,
                                                                       CommandProducer commandProducer,
                                                                       MessageConsumer messageConsumer,
                                                                       SagaLockManager sagaLockManager,
                                                                       SagaCommandProducer sagaCommandProducer) {
        return new SagaManagerImpl<>(saga, sagaInstanceRepository, commandProducer, messageConsumer, sagaLockManager, sagaCommandProducer);
    }
}
