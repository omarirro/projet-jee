package ma.ensias.dao;

import static ma.ensias.dao.DAOUtilitaire.fermeturesSilencieuses;
import static ma.ensias.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.*;

import ma.ensias.beans.Image;

public class ImageDaoImpl implements ImageDao{

	private DAOFactory daoFactory;
	
	private static final String SQL_SELECT_BY_ID = "SELECT id, url FROM content WHERE id = ?";
	private static final String SQL_INSERT = "INSERT INTO content (url) VALUES (?)";
	
	ImageDaoImpl(DAOFactory daoFactory)
	{
		this.daoFactory = daoFactory;
	}
	
	private static Image map(ResultSet resultset) throws SQLException {
		Image image = new Image();
		image.setId(resultset.getInt("id"));
		image.setUrl(resultset.getString("url"));
		return image;
		
	}
	@Override
	public void create(Image image) {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, image.getUrl() );
	        int statut = preparedStatement.executeUpdate();
	        if ( statut == 0 ) {
	            throw new DAOException( "image creation error, no line was inserted" );
	        }
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            image.setId( valeursAutoGenerees.getInt( 1 ) );
	        } else {
	            throw new DAOException( "image creation error in DB, no auto generated ID was returned" );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
		
	}

	@Override
	public Image find(int id) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Image image = null;
	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_ID, false, id );
	        resultSet = preparedStatement.executeQuery();
	        if ( resultSet.next() ) {
	            image = map( resultSet );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }		

		return image;
	}

	@Override
	public void update(Object... fields) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Image image) {
		// TODO Auto-generated method stub
		
	}

}
