class RemoveProfilesColumnFromUsers < ActiveRecord::Migration
  def self.up
    remove_columns :users, :first_name, :last_name, :email, :phone
  end

  def self.down
    add_column :users, :email, :string, :null => false, :limit => 30
    add_column :users, :first_name, :string, :null => false, :limit => 30
    add_column :users, :last_name, :string, :null => false, :limit => 30
    add_column :users, :phone, :string, :null => false, :limit => 20
  end
end
