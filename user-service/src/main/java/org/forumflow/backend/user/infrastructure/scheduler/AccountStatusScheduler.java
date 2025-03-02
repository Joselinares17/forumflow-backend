package org.forumflow.backend.user.infrastructure.scheduler;

import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class AccountStatusScheduler {
    private final Logger log = LoggerFactory.getLogger(AccountStatusScheduler.class);
    private final UserRepository userRepository;

    public AccountStatusScheduler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(fixedRate = 60, timeUnit = TimeUnit.SECONDS)
    public void checkSuspendedAccounts() {
        log.info("Ejecutando verificación programada de cuentas suspendidas");

        List<User> suspendedUsers = userRepository.findAllSuspendedUsers();
        LocalDateTime now = LocalDateTime.now();

        for (User user : suspendedUsers) {
            LocalDateTime suspensionEndTime = user.getSuspensionStart().plus(user.getSuspensionDuration());

            if (now.isAfter(suspensionEndTime)) {
                log.info("Usuario {} con suspensión expirada. Fin de suspensión: {}, Ahora: {}",
                        user.getId(), suspensionEndTime, now);

                user.setAccountNonLocked(true);
                user.setSuspensionStart(null);
                user.setSuspensionDuration(null);
                userRepository.save(user);

                log.info("Usuario {} desuspendido automáticamente por el programador", user.getId());
            } else {
                log.debug("Usuario {} aún suspendido. Fin de suspensión: {}, Ahora: {}",
                        user.getId(), suspensionEndTime, now);
            }
        }
    }

    @Scheduled(fixedRate = 60, timeUnit = TimeUnit.SECONDS)
    public void checkBannedAccounts() {
        log.info("Ejecutando verificación programada de cuentas baneadas");

        List<User> bannedUsers = userRepository.findAllBannedUsers();
        LocalDateTime now = LocalDateTime.now();

        for (User user : bannedUsers) {
            LocalDateTime banEndTime = user.getBanStart().plus(user.getBanDuration());

            if (now.isAfter(banEndTime)) {
                log.info("Usuario {} con baneo expirado. Fin de baneo: {}, Ahora: {}",
                        user.getId(), banEndTime, now);

                user.setEnabled(true);
                user.setBanStart(null);
                user.setBanDuration(null);
                userRepository.save(user);

                log.info("Usuario {} desbaneado automáticamente por el programador", user.getId());
            } else {
                log.debug("Usuario {} aún baneado. Fin de baneo: {}, Ahora: {}",
                        user.getId(), banEndTime, now);
            }
        }
    }
}
