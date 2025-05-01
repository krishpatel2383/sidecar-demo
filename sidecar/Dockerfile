# Use the official Filebeat image
FROM docker.elastic.co/beats/filebeat:7.17.0

# Copy the custom Filebeat configuration file
COPY filebeat.yml /usr/share/filebeat/filebeat.yml

# Set proper ownership and permissions for the Filebeat config and log directory
USER root

# Ensure the /var/log/filebeat directory exists and has the correct permissions
RUN mkdir -p /var/log/filebeat && \
    chown -R filebeat:filebeat /var/log/filebeat && \
    chmod -R 755 /var/log/filebeat && \
    chown root:filebeat /usr/share/filebeat/filebeat.yml && \
    chmod go-w /usr/share/filebeat/filebeat.yml

# Return to filebeat user to avoid running as root
USER filebeat

# Set the working directory to Filebeat's default directory
WORKDIR /usr/share/filebeat

# Start Filebeat in the foreground with the specified config
CMD ["filebeat", "-e", "-c", "/usr/share/filebeat/filebeat.yml"]
