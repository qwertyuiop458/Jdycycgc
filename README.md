# Медбрат: Ночная смена

Теперь в репозитории есть браузерная версия игры, доступная как статический сайт.

## Общедоступная ссылка (без локального сервера)
После пуша в GitHub и выполнения workflow GitHub Pages игра будет доступна по ссылке:
- `https://<ваш-github-username>.github.io/Jdycycgc/`

> Пример: если username = `alex`, ссылка будет `https://alex.github.io/Jdycycgc/`.

В репозитории уже добавлен workflow автопубликации:
- `.github/workflows/deploy-pages.yml`

## Быстрый локальный запуск (опционально)
```bash
python3 -m http.server 8000
```
Открыть: `http://localhost:8000`

## Геймплей
Вы управляете медбратом в больничном коридоре:
- помогаете пациентам (`+10`),
- собираете лекарства (`+5`),
- нельзя пропустить 5 пациентов.

Смена длится 60 секунд.

## Управление
- `←` / `→` — движение
- `P` — пауза
- `R` — новая смена
- Кнопки под игрой дублируют действия

## Файлы
- `index.html` — веб-игра (HTML/CSS/JS)
- `.github/workflows/deploy-pages.yml` — автодеплой в GitHub Pages
- `src/com/medbro/game/NurseShiftMidlet.java` — J2ME MIDlet версия
- `src/com/medbro/game/ShiftCanvas.java` — логика J2ME версии
