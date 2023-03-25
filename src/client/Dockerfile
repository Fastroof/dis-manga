#build
FROM node:19-alpine as build
WORKDIR /app
COPY dis-manga/package.json ./
COPY dis-manga/package-lock.json ./
RUN npm install
COPY dis-manga/. ./
RUN npm run build

#run
FROM nginx:stable-alpine
COPY --from=build /app/dist/dis-manga /usr/share/nginx/html
COPY dis-manga/nginx/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
