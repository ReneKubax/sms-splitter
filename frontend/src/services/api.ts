import axios, { AxiosError } from 'axios';
import type { SmsRequest, SmsResponse, HealthResponse, ApiError } from '../types/api.types';

const API_BASE_URL = 'http://localhost:8080/api/v1';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const smsApi = {
  // Health check
  checkHealth: async (): Promise<HealthResponse> => {
    try {
      const response = await api.get<HealthResponse>('/sms/health');
      return response.data;
    } catch (error) {
      const axiosError = error as AxiosError<ApiError>;
      throw new Error(axiosError.response?.data?.message || 'Failed to check API health');
    }
  },

  // Send SMS
  sendSms: async (request: SmsRequest): Promise<SmsResponse> => {
    try {
      const response = await api.post<SmsResponse>('/sms/send', request);
      return response.data;
    } catch (error) {
      const axiosError = error as AxiosError<ApiError>;
      throw new Error(axiosError.response?.data?.message || 'Failed to send SMS');
    }
  },
};

export default api;