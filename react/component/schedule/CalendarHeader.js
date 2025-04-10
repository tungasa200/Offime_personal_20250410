function CalendarHeader({ currentDate, onClick, handleViewMode }) {
    return (
        <div className="calendar-header mb_md">
            <p id="monthYear" className="cal-month" onClick={onClick}>
                {currentDate.getFullYear()}년 {currentDate.getMonth() + 1}월
            </p>
            <div className="item-flex">
                <button className="btn btn-sm btn-pm-f" onClick={handleViewMode}>보기 변경</button>
            </div>
        </div>
    );
}

export default CalendarHeader;
