package com.landy.service.interview.configuration

import java.time.LocalDateTime
import java.util.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User

@Configuration
class SecurityConfiguration : WebSecurityConfigurerAdapter() {
  // this is to simplify tests, don't do it in production
  override fun configure(http: HttpSecurity) {
    http.authorizeRequests().antMatchers("/").permitAll()
    http.csrf().disable()
  }
}

@Configuration
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware", dateTimeProviderRef = "utcDateTimeProvider")
class AuditingConfiguration {

  @Bean
  fun utcDateTimeProvider(): DateTimeProvider {
    return DateTimeProvider { Optional.of(LocalDateTime.now()) }
  }

  @Bean
  fun springSecurityAuditorAware(): AuditorAware<String> {
    return AuditorAware label@{
      val securityContext = SecurityContextHolder.getContext()
      if (securityContext.authentication == null) {
        return@label Optional.of("system")
      }

      return@label Optional.of((securityContext.authentication.principal as User).username)
    }
  }
}
