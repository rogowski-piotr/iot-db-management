FROM raspbian/stretch:latest

EXPOSE 8080

WORKDIR /app

RUN sudo apt-get update \
	&& apt-get clean -y \
	&& sed -i 's/stretch/buster/g' /etc/apt/sources.list \
	&& apt-get update \
	&& apt-get clean -y \
	&& rm -rf /var/lib/apt/lists/* \
	&& rm -f /usr/sbin/policy-rc.d \
	&& apt-get update \
	&& apt-get install default-jdk -y

