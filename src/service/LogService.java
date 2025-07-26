package service;

import java.sql.SQLException;
import java.util.List;

import dao.LogDAO;
import model.Log;

public class LogService implements ILogService{
	private final LogDAO logDao = LogDAO.getInstance();
	static final LogService logService = new LogService();
	
	public static LogService getInstance() {
		return logService;
	}
	@Override
	public List<Log> getAllLog(int id) {
		// TODO Auto-generated method stub
		try {
			return logDao.getLogByUserId(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void updateLogById(int id, Log log) {
	    try {
	        logDao.updateLogById(id, log);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	@Override
	public Log getLogById(int id) {
		// TODO Auto-generated method stub
		try {
			return logDao.getLogById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int createLog(Log log) {
		// TODO Auto-generated method stub
		try {
			return logDao.insertLog(log);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

}
