import axios from "axios";

function ScheduleDetail({ onClose, onEdit, selectedSchedule, getSchedule }) {
    const getDayOfWeek = (dateString) => {
        const days = ['일', '월', '화', '수', '목', '금', '토'];
        const date = new Date(dateString);
        return days[date.getDay()];
    };

    const id = parseInt(selectedSchedule.id);

    const deleteSched = async ()=>{
        const check = window.confirm("스케줄을 삭제하시겠습니까?");
        if(!check) return;
            try {
                await axios.delete("http://localhost:8080/schedule/delete", { params: { id: id }} );
                console.log("[ScheduleDelete.js] deleteSched() success.")
                onClose();
                getSchedule();

            }catch (error){
                console.error("[ScheduleDelete.js] deleteSched() error.");
                console.error(error);
            }
    }

    return (
        <>
            <div className="sched-date">
                <p className={`fs_lg 
                    ${selectedSchedule.color === 'blue' ? 'tc-pm'
                    : selectedSchedule.color === 'navy' ? 'tc-p2' 
                    : selectedSchedule.color === 'yellow' ? 'tc-p3' 
                    : selectedSchedule.color === 'green' ? 'tc-p4'
                    :selectedSchedule.color === 'red' ? 'tc-p5' : ''}`}>{selectedSchedule.date}  ({getDayOfWeek(selectedSchedule.date)})</p>
            </div>
            <div className="inner">
                <p className="fs_md mt_lg">일정 시간</p>
                    <div className='item-flex mt_md'>
                        <div className="input-time">
                            <p className="fs_md txt-a-c">{(selectedSchedule.startTime).slice(0, 5)}</p>
                        </div>
                        <p className="fs_md">~</p>
                        <div className="input-time">
                            <p className="fs_md txt-a-c">{(selectedSchedule.endTime).slice(0, 5)}</p>
                        </div>
                    </div>
                    <p className="fs_md mt_lg mb_md">휴식 시간</p>
                    <div className="input-time">
                        <p className="fs_md txt-a-c">{(selectedSchedule.breakTime).slice(0, 5)}</p>
                    </div>

                    <p className="fs_md mt_lg">메모</p>
                    <p className='mt_md fs_md input-time'>{selectedSchedule.memo}</p>
                    <div className="item-flex mt_lg">
                        <button
                            className="btn fs_md btn-lg btn-e-f"
                            onClick={deleteSched}
                        >
                            스케줄 삭제
                        </button>
                        <button
                            className="btn fs_md btn-lg btn-pm"
                            onClick={onEdit}
                        >
                            스케줄 수정
                        </button>
                    </div>
                </div>
            </>
            );
            }

            export default ScheduleDetail;
