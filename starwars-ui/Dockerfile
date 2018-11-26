FROM node:10-alpine

WORKDIR /app

RUN pwd
COPY package*.json /app/
RUN npm install
COPY . /app/

EXPOSE 3000

CMD node index.js
