export interface SmsRequest {
  message: string;
  phoneNumber?: string;
}

export interface SmsResponse {
  originalMessage: string;
  totalParts: number;
  parts: string[];
  totalCharacters: number;
}

export interface HealthResponse {
  status: string;
  service: string;
  version: string;
}

export interface ApiError {
  error: string;
  message: string;
  timestamp: string;
  status: number;
}