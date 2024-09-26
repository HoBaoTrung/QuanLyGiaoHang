import { useEffect, useState } from "react";
import { authApi, endpoint } from "../configs/Apis";
import { Bar } from 'react-chartjs-2';
import ChartDataLabels from 'chartjs-plugin-datalabels';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Col, Row, Table } from "react-bootstrap";

// Đăng ký các thành phần của Chart.js
ChartJS.register(
  CategoryScale,  // Đăng ký thang đo kiểu 'category' cho trục x
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend, ChartDataLabels
);

const ColumnChart = ({ year, period, kind }) => {
  const monthOrder = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'];
  const defaultChartData = {
    labels: [1, 2],
    datasets: [
      {
        label: 'TOTAL ' + kind.toUpperCase(),
        data: [3000, 2000],
        backgroundColor: 'rgba(75, 192, 192, 0.6)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1,
      },
    ],
  };
  const [chartData, setChartData] = useState(defaultChartData);
  const [periods, setPeriod] = useState(null)
  const [values, setvalue] = useState(null)

  function extractValue(data, key) {
    return data.map(item => `${item[key]}`);
  }

  const loadData = async () => {
    let form = new FormData()
    form.append("year", year)
    form.append("period", period)
    let res = await authApi().post(endpoint[`admin-statistic-${kind}`], form)
    
    let month = extractValue(res.data, period);
    let value = extractValue(res.data, kind);

    // Kết hợp các giá trị tháng và giá thành một mảng gồm các cặp 
    const combinedData = month.map((month, index) => [month, value[index]]);

    // Sắp xếp các cặp theo thứ tự của tháng
    combinedData.sort((a, b) => monthOrder.indexOf(a[0]) - monthOrder.indexOf(b[0]));
    let sortedMonths = combinedData.map(item => item[0]);
    let sortedvalues = combinedData.map(item => item[1]);

    setvalue(sortedvalues); setPeriod(sortedMonths);

    setChartData(prevChartData => ({
      ...prevChartData,
      labels: sortedMonths,
      datasets: [
        {
          ...prevChartData.datasets[0], // Giữ nguyên các thuộc tính khác của dataset
          data: sortedvalues,
        },
      ],
    }));
  }

  useEffect(() => {
    loadData();
  }, [year, period])

  const options = {
    plugins: {
      datalabels: {
        color: 'black', // Màu chữ của số
        anchor: 'end',
        align: 'top',
        font: {
          weight: 'bold',
        },
        formatter: function (value) {
          return value; // Hiển thị giá trị của cột
        },
      },
    },
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      x: {
        type: 'category',
        title: {
          display: true,
          text: period.toUpperCase(),
        },
      },
      y: {
        beginAtZero: true,
        title: {
          display: true,
          text: kind.toUpperCase(),
        },
      },
    },
  };
  return (
    <><h3>{kind.toUpperCase()} STATISTIC (PAID)</h3>
    <div style={{ display: 'flex', height: '100vh' }}>

      <div style={{ flex: 1, padding: '20px' }}>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>{period.toUpperCase()}</th>
              <th>{kind.toUpperCase()}</th>

            </tr>
          </thead>
          <tbody style={{ overflowY: 'auto', height: '100px' }}>
            {periods !== null ? (periods.map((period, index) => (
              <tr key={index}>
                <td>{period}</td>
                <td>{values[index]}</td>
              </tr>
            ))) : <></>}
          </tbody>
        </Table></div>
      <div style={{ flex: 2, padding: '20px' }}>

        <div style={{ height: '400px', width: '100%' }}>
          <Bar data={chartData} options={options} />
        </div>
      </div>

    </div>
    </>
  );

}
export default ColumnChart