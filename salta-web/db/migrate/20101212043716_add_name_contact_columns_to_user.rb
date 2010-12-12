class AddNameContactColumnsToUser < ActiveRecord::Migration
  def self.up
    add_column :users, :first_name, :string, :null => false, :limit => 30
    add_column :users, :last_name, :string, :null => false, :limit => 30
    add_column :users, :phone, :string, :null => false, :limit => 20
  end

  def self.down
    remove_columns :users, :first_name, :last_name, :phone
  end
end
