# Описание сущностей

## Workout
- Title - название тренировки
- Description - краткое описание тренировки
- Type - тип тренировки [WorkoutType](#WorkoutType)
- Equipment - оборудование для тренировки [Equipment](#Equipment)
- Content
    - video - видеоматериал тренировки (строка с URL видео)
    - steps - текстовое описание шагов тренировки - массив [WorkoutStep](#WorkoutStep)
- Feedback - [Feedback](#Feedback)
- Rating - общая оценка тренировки (Double)

## WorkoutStep
- Title - название шага
- Description - текстовое описание шага

## Feedback
- Review - отзыв пользователя (Текст комментария)
- Rating - оценка пользователя

## WorkoutType
Enum. Тип тренировки по группе мышц.
- Core - пресс и корпус
- Arms - руки и плечи
- Legs - ноги и ягодицы

## Equipment
Enum. Оборудование для тренировки.
- OwnWeight - собственный вес
- Dumbbells - гантели
- JumpingRope - скакалка
- HorizontalBar - турник
- Bars - брусья