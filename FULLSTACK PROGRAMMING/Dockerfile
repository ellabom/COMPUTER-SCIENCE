FROM node:latest
EXPOSE 8080
WORKDIR usr/src/app
COPY package*.json ./
COPY server.js .
RUN npm add express
RUN npm add body-parser
COPY . .
EXPOSE 8080
CMD ['node', "server.js"]