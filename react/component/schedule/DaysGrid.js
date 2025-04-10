function DaysGrid({ renderDays, activeDay, onSelectDay, schedules, onScheduleClick, viewMode }) {
    return (
        <>
            <div className="weekdays">
                {["일", "월", "화", "수", "목", "금", "토"].map((day) => (
                    <div key={day}>{day}</div>
                ))}
            </div>
            <div className="days">
                {renderDays.map((date, index) => {
                    const scheduleItems = schedules.filter(s =>
                        s.date === `${date.year}-${String(date.month + 1).padStart(2, '0')}-${String(date.day).padStart(2, '0')}`
                    );
                    const isActive =
                        activeDay &&
                        activeDay.year === date.year &&
                        activeDay.month === date.month &&
                        activeDay.day === date.day;

                    return (
                        <div
                            key={index}
                            className={`day ${date.today ? "today" : ""} ${date.inactive ? "inactive" : ""} ${isActive ? "active" : ""}`}
                            onClick={() => onSelectDay(date)}
                        >
                            <p>{date.day}</p>
                            {scheduleItems.map((item, idx) => (
                                <div
                                    key={idx}
                                    className={`sched ${item.color}`}
                                    onClick={() => onScheduleClick(item)}
                                >
                                    <p className="fs_xsm active">
                                        {viewMode ? (
                                            <>
                                                {item.memo}
                                            </>
                                        ) : (
                                            <>
                                                {(item.startTime).slice(0,5)}
                                            </>
                                        )}
                                    </p>
                                </div>
                            ))}
                        </div>
                    );
                })}
            </div>
        </>
    );
}

export default DaysGrid;
