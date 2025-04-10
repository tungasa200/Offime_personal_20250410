import {useNavigate} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import axios from "axios";
import CalendarHeader from "./CalendarHeader";
import DaysGrid from "./DaysGrid";
import ScheduleForm from "./ScheduleForm";
import ScheduleDetail from "./ScheduleDetail";
import YearMonthPicker from "./YearMonthPicker";
import {HttpHeadersContext} from "../context/HttpHeadersProvider";
import {renderCalendar} from "./utils/CalendarUtils";
import ScheduleEditForm from "./ScheduleEditForm";

function CalendarContainer() {
    const { headers } = useContext(HttpHeadersContext);
    const today = new Date();
    const navigate = useNavigate();

    const [currentDate, setCurrentDate] = useState(new Date());
    const [activeDay, setActiveDay] = useState(null);
    const defaultDate = (today.getFullYear() + "-" + (today.getMonth() +1) + "-" + today.getDate()).toString() ;
    const [selectedDate, setSelectedDate] = useState(defaultDate);
    const [selectedDay, setSelectedDay] = useState(['일', '월', '화', '수', '목', '금', '토'][today.getDay()]);
    const [selectedYear, setSelectedYear] = useState(today.getFullYear());
    const [selectedMonth, setSelectedMonth] = useState(today.getMonth() + 1);

    const [writeOpen, setWriteOpen] = useState(false);
    const [yearMonthOpen, setYearMonthOpen] = useState(false);
    const [editOpen, setEditOpen] = useState(false);
    const [schedules, setSchedules] = useState([]);
    const [startTime,setStartTime] = useState("");
    const [endTime, setEndTime] = useState("");
    const [breakTime, setBreakTime] = useState("");
    const [memo,setMemo] = useState("");
    const colors = [ 'blue', 'navy', 'yellow', 'green', 'red' ];
    const [schedColor, setSchedColor] = useState('blue');
    const [selectedColorIndex, setSelectedColorIndex] = useState(0);
    const [selectedSchedule, setSelectedSchedule] = useState(null);
    const [detailOpen, setDetailOpen] = useState(false);
    const [isEditMode, setIsEditMode] = useState(false);
    const [viewMode,setViewMode] = useState(false);

    useEffect(() => {
        getSchedule();
    }, []);

    const getSchedule = async () => {
        await axios.get("http://localhost:8080/schedule/list", {
            params: { year: selectedYear, month: selectedMonth },
            headers
        }).then(response => {
            setSchedules(response.data);
        }).catch(error => {
            console.error("스케줄을 가져올 수 없습니다.", error);
        });
    };

    const writeSchedule = async () => {
        try {
            const req = {
                memberId : parseInt(localStorage.getItem('id'),10),
                date : selectedDate,
                startTime,
                endTime,
                breakTime,
                memo,
                color : schedColor,
            };
            const response = await axios.post("http://localhost:8080/schedule/write", req);
            setWriteOpen(false);
            getSchedule();
        } catch (error){
            console.error("스케줄등록에 실패했습니다.", error);
        }
    };

    const handleSelectDay = (dayObj) => {
        setActiveDay(dayObj);
        setSelectedDate(`${dayObj.year}-${String(dayObj.month + 1).padStart(2, "0")}-${String(dayObj.day).padStart(2, "0")}`);
        setSelectedDay(dayObj.dayName);
    };

    const handleEdit = () => setWriteOpen(prev => !prev);
    const handleYearMonth = () => setYearMonthOpen(prev => !prev);
    const handleSelect = (month) => setSelectedMonth(month);
    const increaseYear = () => setSelectedYear(prev => prev + 1);
    const decreaseYear = () => setSelectedYear(prev => prev - 1);
    const handleConfirmYM = () => {
        setCurrentDate(new Date(selectedYear, selectedMonth - 1));
        setYearMonthOpen(false);
    };
    const handleCancelYM = () => setYearMonthOpen(false);
    const handleSchedColor = (index) => {
        setSelectedColorIndex(index);
        setSchedColor(colors[index]);
    };
    const handleScheduleClick = (schedule) => {
        setSelectedSchedule(schedule);
        setDetailOpen(true);
    };

    const handleViewMode= ()=>{
        setViewMode(prev => !prev);
    }

    return (
        <>
            <div className={writeOpen ? "calendar-container active" : "calendar-container"}>
                <p className="hidden">calendar page</p>
                <div className="calender-wrap">
                    <div className="calendar">
                        <CalendarHeader currentDate={currentDate} onClick={handleYearMonth} handleViewMode={handleViewMode}/>
                        <DaysGrid
                            renderDays={renderCalendar(currentDate, schedules)}
                            activeDay={activeDay}
                            onSelectDay={handleSelectDay}
                            schedules={schedules}
                            onScheduleClick={handleScheduleClick}
                            viewMode={viewMode}
                        />
                    </div>
                </div>
            </div>

            <div
                className={`dark-area ${ yearMonthOpen && !writeOpen || detailOpen && !writeOpen ? 'active' : ''}`}
                onClick={() => {
                    setYearMonthOpen(false);
                    setDetailOpen(false);
                    setIsEditMode(false);
                }}></div>

            <div className={`schedule-detail ${writeOpen || yearMonthOpen || detailOpen ? 'active' : ''}`}>
                {writeOpen && (
                    <ScheduleForm
                        selectedDate={selectedDate}
                        selectedDay={selectedDay}
                        colors={colors}
                        selectedColorIndex={selectedColorIndex}
                        handleSchedColor={handleSchedColor}
                        setStartTime={setStartTime}
                        setEndTime={setEndTime}
                        setBreakTime={setBreakTime}
                        setMemo={setMemo}
                        writeSchedule={writeSchedule}
                    />
                )}

                {!writeOpen && yearMonthOpen && (
                    <YearMonthPicker
                        selectedYear={selectedYear}
                        selectedMonth={selectedMonth}
                        increaseYear={increaseYear}
                        decreaseYear={decreaseYear}
                        handleSelect={handleSelect}
                        handleCancelYM={handleCancelYM}
                        handleConfirmYM={handleConfirmYM}
                    />
                )}

                {!writeOpen && detailOpen && (
                    <>
                        {isEditMode ? (
                            <ScheduleEditForm
                                schedule={selectedSchedule}
                                colors={colors}
                                selectedColorIndex={selectedColorIndex}
                                handleSchedColor={handleSchedColor}
                                schedColor={schedColor}
                                onClose={() => setIsEditMode(false)}
                                onUpdated={() => {
                                    setIsEditMode(false);
                                    setDetailOpen(false);
                                    getSchedule();
                                }}
                            />
                        ) : (
                           <>
                               {selectedSchedule && (
                                   <ScheduleDetail
                                       selectedSchedule={selectedSchedule}
                                       onClose={() => setDetailOpen(false)}
                                       onEdit={() => setIsEditMode(true)}
                                       getSchedule={getSchedule()}
                                   />
                               )}
                           </>
                        )}
                    </>
                )}
            </div>

            <div className="sched-btn" onClick={handleEdit}>
                {writeOpen ? (
                    <img src="/image/icon_close.svg" alt="스케줄 추가 닫기" />
                ) : (
                    <img src="/image/icon_write.svg" alt="스케줄 추가 열기" />
                )}
            </div>
        </>
    );
}

export default CalendarContainer;
