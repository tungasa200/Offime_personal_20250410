import {useState} from "react";

function ScheduleForm({
                          selectedDate, selectedDay, colors, selectedColorIndex,
                          handleSchedColor, setStartTime, setEndTime, setBreakTime, setMemo, writeSchedule
                      }) {
    const [newStartTime, setNewStartTime] = useState('');
    const [newEndTime, setNewEndTime] = useState('');
    const [newBreakTime, setNewBreakTime] = useState('');
    const [newMemo,setNewMemo] = useState('');

    const handleSubmit =()=>{
        if(!newStartTime || !newEndTime ||  !newMemo){
            alert('모든 항목을 입력해주세요.');
            return;
        }

        const start = new Date(`1970-01-01T${newStartTime}:00`);
        const end = new Date(`1970-01-01T${newEndTime}:00`);
        const breakT = new Date(`1970-01-01T${newBreakTime}:00`);

        if (end <= start) {
            alert('종료 시간은 시작 시간보다 이후여야 합니다.');
            return;
        }

        if (breakT <= start) {
            alert('휴식 시간은 시작 시간보다 이후여야 합니다.');
            return;
        }

        if (breakT > end) {
            alert('휴식 시간은 종료 시간 전이어야 합니다.');
            return;
        }

        setStartTime(newStartTime);
        setEndTime(newEndTime);
        setBreakTime(newBreakTime);
        setMemo(newMemo);

        writeSchedule();
    }

    return (
        <>
            <div className="sched-date">
                <input type="text" value={selectedDate + ` ` + `(` + selectedDay + `)`} readOnly id="selectedDate" />
            </div>
            <div className="inner">
                <ul className="color_list">
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
                    <input type="time" className='input-time' id="start-time" onChange={(e) => setNewStartTime(e.target.value)} /> <p className="fs_md">~</p>
                    <input type="time" className='input-time' id="end-time" onChange={(e) => setNewEndTime(e.target.value)} />
                </div>
                <p className="fs_md mt_lg mb_md">휴식 시간</p>
                <input type="time" className='input-time' id="break-time" onChange={(e) => setNewBreakTime(e.target.value)} />
                <p className="fs_md mt_lg">메모</p>
                <textarea className='textarea mt_md fs_md' rows={5} onChange={(e) => setNewMemo(e.target.value)}></textarea>
                <button className="btn btn-max btn-pm fs_md mt_lg" onClick={handleSubmit}>스케줄 등록</button>
            </div>
        </>
    );
}

export default ScheduleForm;
