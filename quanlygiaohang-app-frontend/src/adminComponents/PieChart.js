import { useEffect, useState } from "react";
import { authApi, endpoint } from "../configs/Apis";
import { Pie } from 'react-chartjs-2';
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
import { ArcElement } from "chart.js";

// Đăng ký các thành phần của Chart.js
ChartJS.register(
  CategoryScale,  // Đăng ký thang đo kiểu 'category' cho trục x
  LinearScale,
  BarElement,
  Title,
  Tooltip, ArcElement,
  Legend, ChartDataLabels
);

const PieChart = ({ year }) => {
  // const monthOrder = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'];
  const defaultChartData = {
    labels: [1, 2],
    datasets: [
      {
        label: 'Total Price',
        data: [3000, 2000],
        backgroundColor: 'rgba(75, 192, 192, 0.6)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1,
      },
    ],
  };
  const [chartData, setChartData] = useState(defaultChartData);
  // const [periods, setPeriod] = useState(null)
  // const [prices, setPrice] = useState(null)
  // function extractPeriods(data, key) {
  //   return data.map(item => `${item[key]}`);
  // }
  const loadData = async () => {
    let form = new FormData()
    form.append("year", year)
    // form.append("period", period)
    let res = await authApi().post(endpoint['admin-statistic-service'], form)
    console.info(res)

    const services = res.data.map(item => item.serviceName);
    const counts = res.data.map(item => item.quantity);
    console.info(services)
    console.info(counts)
    // Kết hợp các giá trị tháng và giá thành một mảng gồm các cặp [tháng, giá]

    setChartData(prevChartData => ({
      ...prevChartData,
      labels: services,
      datasets: [
        {
          ...prevChartData.datasets[0], // Giữ nguyên các thuộc tính khác của dataset
          data: counts,
          label: 'Service Count',
          backgroundColor: [
            'rgba(255, 99, 132, 0.6)',
            'rgba(54, 162, 235, 0.6)',
            'rgba(255, 206, 86, 0.6)',
            'rgba(75, 192, 192, 0.6)',
            'rgba(153, 102, 255, 0.6)'
          ]
        },
      ],
    }));
    console.info(chartData)
  }

  useEffect(() => {
    loadData();
    console.log('Updated chartData:', chartData);
  }, [year])

  return (
    <>
      <h3>SERVICE STATISTICS (YEAR)</h3>
      <div style={{ display: 'flex', height: '70%' }}>
        <Pie data={chartData} />
      </div>


    </>
  );

}
export default PieChart