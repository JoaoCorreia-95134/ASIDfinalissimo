FROM openjdk:22 


COPY /target/*.jar app.jar


# Expose the port your application listens on
EXPOSE 8085  
# Replace with your actual application port

# Run the application
CMD ["java", "-jar", "app.jar"]
