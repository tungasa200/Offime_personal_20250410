function YearMonthPicker({ selectedYear, selectedMonth, increaseYear, decreaseYear, handleSelect, handleCancelYM, handleConfirmYM }) {
    return (
        <div className="ym-picker">
            <div className="year-picker">
                <button onClick={decreaseYear} className='fs_lg'>{'<'}</button>
                <p className='fs_lg'>{selectedYear}</p>
                <button onClick={increaseYear} className='fs_lg'>{'>'}</button>
            </div>

            <div className="month-picker">
                {[...Array(12)].map((_, i) => {
                    const month = i + 1;
                    return (
                        <div
                            key={month}
                            className={`month ${selectedMonth === month ? 'selected' : ''}`}
                            onClick={() => handleSelect(month)}
                        >
                            <p className='fs_lg'>{month.toString().padStart(2, '0')}</p>
                        </div>
                    );
                })}
            </div>

            <div className="selected-ym mb_lg">
                <p className='fs_md'>{selectedYear}년 {selectedMonth.toString().padStart(2, '0')}월</p>
            </div>
            <div className="btn-box">
                <button className='btn btn-md btn-e-f fs_md' onClick={handleCancelYM}>취소</button>
                <button className='btn btn-md btn-pm-f fs_md' onClick={handleConfirmYM}>확인</button>
            </div>
        </div>
    );
}

export default YearMonthPicker;
