start:
	./gradlew build
	docker build -t rest-spring-boot-java-users .
	docker-compose up -d

stop:
	docker-compose down
