import Button from 'react-bootstrap/Button';
const ButtonCheckPay = ({ onCheck }) => {

    
    return (
        <>
            <Button onClick={()=>onCheck(false)} variant="warning" className='m-3'>Chưa thanh toán</Button>
            <Button onClick={()=>onCheck(true)} variant="success">Đã thanh toán</Button>

        </>
    );
}
export default ButtonCheckPay