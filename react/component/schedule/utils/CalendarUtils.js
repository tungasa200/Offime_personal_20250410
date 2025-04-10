export function renderCalendar(currentDate, schedules) {

    const today = new Date();
    const dayNames = ['일', '월', '화', '수', '목', '금', '토'];
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();
    const firstDay = new Date(year, month, 1).getDay();
    const lastDay = new Date(year, month + 1, 0).getDay();
    const lastDate = new Date(year, month + 1, 0).getDate();
    const prevLastDate = new Date(year, month, 0).getDate();

    const daysArray = [];

    for (let i = firstDay - 1; i >= 0; i--) {
        const date = new Date(year, month - 1, prevLastDate - i);
        const prevMonth = month === 0 ? 11 : month - 1;
        const prevYear = month === 0 ? year - 1 : year;
        const scheduleItems = schedules.filter(s =>
            s.date === `${prevYear}-${String(prevMonth + 1).padStart(2, '0')}-${String(prevLastDate - i).padStart(2, '0')}`
        );
        daysArray.push({
            year: prevYear, month: prevMonth, day: prevLastDate - i,
            dayName: dayNames[date.getDay()],
            inactive: true, schedules: scheduleItems
        });
    }

    for (let i = 1; i <= lastDate; i++) {
        const date = new Date(year, month, i);
        const isToday = year === today.getFullYear() && month === today.getMonth() && i === today.getDate();
        const scheduleItems = schedules.filter(s =>
            s.date === `${year}-${String(month + 1).padStart(2, '0')}-${String(i).padStart(2, '0')}`
        );
        daysArray.push({
            year, month, day: i, dayName: dayNames[date.getDay()],
            today: isToday, inactive: false, schedules: scheduleItems
        });
    }

    for (let i = 1; i < 7 - lastDay; i++) {
        const date = new Date(year, month + 1, i);
        const nextMonth = month === 11 ? 0 : month + 1;
        const nextYear = month === 11 ? year + 1 : year;
        const scheduleItems = schedules.filter(s =>
            s.date === `${nextYear}-${String(nextMonth + 1).padStart(2, '0')}-${String(i).padStart(2, '0')}`
        );
        daysArray.push({
            year: nextYear, month: nextMonth, day: i,
            dayName: dayNames[date.getDay()],
            inactive: true, schedules: scheduleItems
        });
    }

    return daysArray;
}
