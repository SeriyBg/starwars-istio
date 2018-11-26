FROM node:10-alpine

WORKDIR /app

RUN pwd
COPY package*.json /app/
RUN npm install
COPY . /app/

EXPOSE 3000

RUN ls -la
CMD node index.js
