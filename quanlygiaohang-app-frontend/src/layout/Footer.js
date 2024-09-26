import 'bootstrap/dist/css/bootstrap.min.css';
import '../static/css/Footer.css';
import '../static/css/Icon.css';
import { FaMapMarkerAlt, FaEnvelope, FaPhone } from 'react-icons/fa';
import { Col, Container, Nav, Row } from 'react-bootstrap';
const Footer = () => {
  return (
    <div class="mt-4 p-5 footer">
      
      <Row>
        <Col className='mr-1'>
          <h6>CÔNG TY CỔ PHẦN DỊCH VỤ GIAO HÀNG</h6>
          Công ty giao nhận đầu tiên tại Việt Nam được thành lập với sứ mệnh phục vụ nhu cầu vận chuyển chuyên nghiệp của các đối tác Thương mại điện tử trên toàn quốc.
          Giấy CNĐKDN số 0311907295 do Sở Kế Hoạch và Đầu Tư TP HCM cấp lần đầu ngày 02/08/2012, cấp thay đổi lần thứ 20: ngày 25/06/2024. Giấy phép bưu chính số 310/GP-BTTTT do Bộ Thông tin và Truyền thông cấp lần đầu ngày 19/11/2014, cấp điều chỉnh lần thứ 2: ngày 02/08/2019.
          Văn bản xác nhận thông báo hoạt động bưu chính số 2438/XN-BTTTT do Bộ Thông tin và Truyền thông cấp lần đầu ngày 01/02/2013, cấp điều chỉnh lần thứ 2: ngày 26/07/2019.

        </Col>
        
        <Col >
          <h6>VỀ CÔNG TY</h6>
          <Nav.Link href="#home">Giới thiệu</Nav.Link>
          <Nav.Link href="#link">Hệ thống bưu cục</Nav.Link>
          <Nav.Link href="#link">Công nghệ</Nav.Link>
          <Nav.Link href="#link">Tuyển dụng</Nav.Link>
          <Nav.Link href="#link">Liên hệ</Nav.Link>
        </Col>
        <Col> 
        <h6>THÔNG TIN DỊCH VỤ</h6>
          <Nav.Link href="#home">Bản giá</Nav.Link>
          <Nav.Link href="#link">Dịch vụ giao hàng</Nav.Link>
          <Nav.Link href="#link">Câu hỏi thường gặp</Nav.Link>
          </Col>
          <Col> 
        
          </Col>
      </Row>
      <div className="separator"></div>
      <br />
      <div className="contact-info">
      <div className="contact-item">
        <FaMapMarkerAlt className="icon" />
        <span className="text">123 Main Street, Anytown, USA</span>
      </div>
      <div className="contact-item">
        <FaEnvelope className="icon" />
        <span className="text">info@example.com</span>
      </div>
      <div className="contact-item">
        <FaPhone className="icon" />
        <span className="text">+1 234 567 890</span>
      </div>
    </div>
      
    </div>
  )
}
export default Footer;