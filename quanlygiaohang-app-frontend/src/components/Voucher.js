import React, { useEffect, useRef, useState } from 'react';
import '../static/css/ProfileCard.css';
import '../static/css/StarRating.css';
import { Container, Spinner, Table } from 'react-bootstrap';
import { authApi, endpoint } from '../configs/Apis';

const Voucher = () => {
  const [vouchers, setVouchers] = useState(null)
  const [loading, setLoading] = useState(false)
  const loadProduct = async () => {
    try {

      let url = `${endpoint['get-owner-voucher']}`

      const res = await authApi().get(url)
      setVouchers(res.data)
    }
    catch (ex) { console.log(ex) }
    finally { setLoading(false) }
  }

  useEffect(() => {
    loadProduct();

  }, []);

  if (vouchers === null)
    return <Spinner animation="border" />;

  return (
    <Container>
      <Table striped bordered hover>
        <thead>
          <tr>
           
            <th>Tên</th>
            <th>Trị giá</th>
            <th>Số lượng</th>
            <th>Ngày hết hạn</th>
          </tr>
        </thead>
        <tbody>
          {vouchers.map(v =>
            <tr>
              
              <td>{v.name}</td>
              <td>{v.value}{v.measurement}</td>
              <td>{v.quantity}</td>
              <td>{v.expirationDate}</td>
            </tr>
          )


          }
        </tbody>
      </Table>
    </Container>
  );
}
export default Voucher;