# Репозиторій для виконання лабораторних робіт з дисципліни "Розподілені інформаційні системи"

## Як використовувати

В цьому репозиторії знаходиться шаблон для виконання лабораторних робіт.

Для виконання лабораторних робіт необхідно зробити ```fork``` цього репозіторію, склонувати вже власний репозіторій та розміщувати документацію у відповідних діректоріях ```./docs```.

В цьому файлі необхідно вказати назву проекту. Коротку загальну характеристику проекту, контактні дані виконавців.


Шаблон публікування підготовлено з використанням [VuePress](https://vuepress.vuejs.org/), та стартера
[FriendlyUser/vuepress-theme-cool-starter](https://github.com/FriendlyUser/vuepress-theme-cool-starter).

Щоб опублікувати проект у Github Pages, налаштовуємо Github Pages (гілка ```gh-pages```), змінюємо файл ```./publish.sh```

```sh {24}

#!/usr/bin/env sh

# abort on errors
set -e

# build
npm run docs:build

# navigate into the build output directory
cd docs/.vuepress/dist

# if you are deploying to a custom domain
# echo 'www.example.com' > CNAME

git init
git add -A
git commit -m 'deploy'

# if you are deploying to https://<USERNAME>.github.io
# git push -f git@github.com:boldak/<USERNAME>.github.io.git master

# if you are deploying to https://<USERNAME>.github.io/<REPO>
git push -f https://github.com/Fastroof/dis-manga.git master:gh-pages

cd -

```

Потім запускаємо

```bash
    npm run publish
```

Для відлагодження документації в локальному режимі запускаємо

```bash
    npm run docs:dev
```

Доступ до локально опублікованої версії документації [http://localhost:3030](http://localhost:3030)

***Happy learning! Happy coding!***
