import React, { useState } from 'react';
import axios from 'axios';
import {renderCalendar} from "./utils/CalendarUtils";

const ScheduleEditForm = ({ schedule, onClose, onUpdated, colors, selectedColorIndex,
                              handleSchedColor,schedColor, randerDays }) => {
    const [date, setDate] = useState(schedule.date);
    const [startTime, setStartTime] = useState(schedule.startTime);
    const [endTime, setEndTime] = useState(schedule.endTime);
    const [breakTime, setBreakTime] = useState(schedule.breakTime);
    const [memo, setMemo] = useState(schedule.memo);

    const updateSchedule = async (e) => {
        e.preventDefault();
        try {
            const req = {
                id: parseInt( schedule.id,10),
                memberId: parseInt( schedule.memberId,10),
                date,
                startTime ,
                endTime,
                breakTime,
                memo,
                color : schedColor,
            };
            console.log(req)
            await axios.patch('http://localhost:8080/schedule/update', req);
            console.log("[ScheduleEditForm.js] updateSchedule() success.")
            onUpdated();
            randerDays();
        } catch (error) {
            console.error('스케줄 수정 실패:', error);
        }
    };

    return (
        <div className="schedule-detail active">
            <form onSubmit={updateSchedule} id="schedule-edit-form" style={{height:"100%"}}>
                <div className="sched-date">
                    <input
                        id="selectedDate"
                        type="date"
                        value={date}
                        onChange={(e) => setDate(e.target.value)}
                        style={{cursor:"pointer"}}
                    />
                </div>
                <div className="inner">
                    <ul className="color_list mb_lg">
                        {colors.map((color, index) => {
                            const baseClass = index === 0 ? 'bg_pm' : `bg_p0${index + 1}`;
                            return (
                                <li
                                    key={index}
                                    className={`${baseClass} ${selectedColorIndex === index ? 'selected' : ''}`}
                                    onClick={() => handleSchedColor(index)}
                                ></li>
                            );
                        })}
                    </ul>
                    <p className="fs_md mt_lg">일정 시간</p>
                    <div className='item-flex mt_md'>
                        <input
                            type="time"
                            className="input-time mt_md mb_lg"
                            id="startTime"
                            value={startTime}
                            onChange={(e) => setStartTime(e.target.value)}
                        />
                        <p className="fs_md">~</p>
                        <input
                            type="time"
                            className="input-time mt_md mb_lg"
                            id="endTime"
                            value={endTime}
                            onChange={(e) => setEndTime(e.target.value)}
                        />
                    </div>

                    <label className="fs_md" htmlFor="breakTime">휴식 시간</label>
                        <input
                            type="time"
                            className="input-time mt_md mb_lg"
                            id="breakTime"
                            value={breakTime}
                            onChange={(e) => setBreakTime(e.target.value)}
                        />

                        <div className="mb_md">
                            <label className="fs_md" htmlFor="memo">메모</label>
                            <textarea
                                className="textarea mt_md"
                                id="memo"
                                rows="4"
                                value={memo}
                                onChange={(e) => setMemo(e.target.value)}
                            />
                        </div>

                        <div className="item-flex mt_lg">
                            <button type="button" className="btn btn-lg fs_md  btn-e-f" onClick={onClose}>취소</button>
                            <button type="submit" className="btn btn-lg fs_md btn-pm">수정 완료</button>
                        </div>
                    </div>
            </form>
        </div>
    );
};

export default ScheduleEditForm;
