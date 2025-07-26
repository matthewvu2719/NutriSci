package service;

import java.util.List;

import model.Log;

public interface ILogService {
	public List<Log> getAllLog(int id);
	public Log getLogById(int id);
	public int createLog(Log log);
	void updateLogById(int id, Log log);

}
