package com.proyecto.integrador1.proyecto_integrador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.proyecto.integrador1.proyecto_integrador.entities.Rol;
import com.proyecto.integrador1.proyecto_integrador.repositories.RolRepository;


@SpringBootApplication
public class ProyectoIntegradorApplication{

	@Autowired
	private RolRepository rolRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProyectoIntegradorApplication.class, args);
        BCryptPasswordEncoder encoder1 =new BCryptPasswordEncoder();
		String password1=encoder1.encode("12345");
		System.out.println(password1);
        BCryptPasswordEncoder encoder2 =new BCryptPasswordEncoder();
		String password2=encoder2.encode("12345");
		System.out.println(password2);
        BCryptPasswordEncoder encoder3 =new BCryptPasswordEncoder();
		String password3=encoder3.encode("12345");
		System.out.println(password3);
		
		System.out.println();

	}
	@Configuration
	public static class Myconfiguration{
		@Bean
		public WebMvcConfigurer corsConfigurer(){
			return new WebMvcConfigurer() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/**")
							.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
				}
			};
		}
	}


}
